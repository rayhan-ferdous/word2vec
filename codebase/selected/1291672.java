package org.ujorm.orm;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import org.ujorm.logger.UjoLogger;
import org.ujorm.UjoProperty;
import org.ujorm.core.UjoManager;
import org.ujorm.core.UjoManagerXML;
import org.ujorm.CompositeProperty;
import org.ujorm.logger.UjoLoggerFactory;
import org.ujorm.orm.metaModel.MetaDatabase;
import org.ujorm.orm.metaModel.MetaRoot;
import org.ujorm.orm.metaModel.MetaColumn;
import org.ujorm.orm.metaModel.MetaParams;
import org.ujorm.orm.metaModel.MetaProcedure;
import org.ujorm.orm.metaModel.MetaRelation2Many;
import org.ujorm.orm.metaModel.MetaTable;

/**
 * The basic class for an ORM support.
 * @author Pavel Ponec
 * @composed 1 - 1 MetaRoot
 * @assoc - - - AbstractMetaModel
 */
public class OrmHandler {

    /** Logger */
    private static final UjoLogger LOGGER = UjoLoggerFactory.getLogger(OrmHandler.class);

    /** Default handler */
    private static OrmHandler handler = new OrmHandler();

    /** List of databases */
    private MetaRoot databases = new MetaRoot();

    /** Temporary configuration */
    private MetaRoot configuration;

    /** The default ORM session */
    private Session session;

    /** Map a property to a database column model */
    private final HashMap<UjoProperty, MetaRelation2Many> propertyMap = new HashMap<UjoProperty, MetaRelation2Many>();

    /** Map a Java class to a database table model */
    private final HashMap<Class, MetaTable> entityMap = new HashMap<Class, MetaTable>();

    /** Map a Java class to a procedure model */
    private final HashMap<Class, MetaProcedure> procedureMap = new HashMap<Class, MetaProcedure>();

    /** The constructor */
    public OrmHandler() {
    }

    /** The constructor with a database metamodel initialization. */
    public <UJO extends OrmUjo> OrmHandler(final Class<UJO> databaseModel) {
        this();
        loadDatabase(databaseModel);
    }

    /** The constructor with a database metamodel initialization. */
    public <UJO extends OrmUjo> OrmHandler(final Class<UJO>... databaseModels) {
        this();
        loadDatabase(databaseModels);
    }

    public static OrmHandler getInstance() {
        return handler;
    }

    /** Get a <strong>default</strong> Session of the OrmHandler.
      * On a multi-thread application use a method {@link #createSession()} rather.
      * @see #createSession()
      */
    public Session getSession() {
        if (session == null) {
            session = createSession();
        }
        return session;
    }

    /** Create new session */
    public Session createSession() {
        return new Session(this);
    }

    /** Load parameters from an external XML file.
     * The initialization must be finished before an ORM definition loading.
     * <br/>Note: in case the parameter starts by the character tilde '~' than the symbol is replaced by a local home directory.
     * See some valid parameter examples:
     * <ul>
     *    <li>http://myproject.org/dbconfig.xml</li>
     *    <li>file:///C:/Documents%20and%20Settings/my/app/dbconfig.xml</li>
     *    <li>~/app/dbconfig.xml</li>
     * </ul>
     */
    public boolean config(String url) throws IllegalArgumentException {
        try {
            if (url.startsWith("~")) {
                final String file = System.getProperty("user.home") + url.substring(1);
                return config(new File(file).toURI().toURL(), true);
            } else {
                return config(new URL(url), true);
            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Configuration file is not valid " + url, e);
        }
    }

    /** Save the ORM parameters.
     * The assigning must be finished before an ORM definition loading.
     */
    public void config(MetaParams params) throws IllegalArgumentException {
        MetaRoot.PARAMETERS.setValue(databases, params);
    }

    /** Save the alternative ORM configuration include parameters (if the parameters are not null).
     * The assigning must be finished before an ORM definition loading.
     */
    public void config(MetaRoot config) throws IllegalArgumentException {
        this.configuration = config;
        MetaParams params = MetaRoot.PARAMETERS.of(configuration);
        if (params != null) {
            config(params);
        }
    }

    /** Load parameters from an external XML file.
     * The initialization must be finished before an ORM definition loading.
     */
    public boolean config(URL url, boolean throwsException) throws IllegalArgumentException {
        try {
            final MetaRoot conf = UjoManagerXML.getInstance().parseXML(new BufferedInputStream(url.openStream()), MetaRoot.class, this);
            config(conf);
            return true;
        } catch (Exception e) {
            if (throwsException) {
                throw new IllegalArgumentException("Configuration file is not valid ", e);
            } else {
                return false;
            }
        }
    }

    /** Is the parameter a persistent property? */
    public boolean isPersistent(UjoProperty property) {
        final boolean resultFalse = property.isTypeOf(List.class) || UjoManager.getInstance().isTransientProperty(property);
        return !resultFalse;
    }

    /** LoadInternal a database model from paramater */
    private <UJO extends OrmUjo> MetaDatabase loadDatabaseInternal(Class<UJO> databaseModel) {
        String databaseId = databaseModel.getSimpleName();
        MetaDatabase paramDb = configuration != null ? configuration.removeDb(databaseId) : null;
        UJO root = getInstance(databaseModel);
        MetaDatabase dbModel = new MetaDatabase(this, root, paramDb, databases.getDatabaseCount());
        databases.add(dbModel);
        return dbModel;
    }

    /** Load a meta-data, lock it and create database tables.
     * There is not allowed to make any change to the created meta-model.
     */
    @SuppressWarnings("unchecked")
    public final <UJO extends OrmUjo> void loadDatabase(final Class<UJO> databaseModel) {
        loadDatabase(new Class[] { databaseModel });
    }

    /** Load a meta-data, lock it and create database tables.
     * There is not allowed to make any change to the created meta-model.
     */
    public synchronized <UJO extends OrmUjo> void loadDatabase(final Class<UJO>... databaseModel) {
        if (isReadOnly()) {
            throw new IllegalArgumentException("The meta-model is locked and can´t be changed.");
        }
        for (Class<UJO> db : databaseModel) {
            loadDatabaseInternal(db);
        }
        MetaParams params = getParameters();
        for (MetaRelation2Many r : propertyMap.values()) {
            if (r.isColumn()) {
                ((MetaColumn) r).initTypeCode();
            }
        }
        databases.setReadOnly(true);
        final Level level = MetaParams.LOG_METAMODEL_INFO.of(params) ? Level.INFO : Level.FINE;
        if (LOGGER.isLoggable(level)) {
            final String msg = "DATABASE META-MODEL:\n" + getConfig();
            LOGGER.log(level, msg);
        }
        final File outConfigFile = MetaParams.SAVE_CONFIG_TO_FILE.of(getParameters());
        if (outConfigFile != null) try {
            databases.print(outConfigFile);
        } catch (IOException e) {
            throw new IllegalStateException("Can't create configuration " + outConfigFile, e);
        }
        for (MetaDatabase dbModel : getDatabases()) {
            switch(MetaDatabase.ORM2DLL_POLICY.of(dbModel)) {
                case CREATE_DDL:
                case CREATE_OR_UPDATE_DDL:
                case VALIDATE:
                    dbModel.create(getSession());
            }
        }
    }

    /** Create an instance from the class */
    private <UJO extends OrmUjo> UJO getInstance(Class<UJO> databaseModel) {
        try {
            return databaseModel.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Can't create instance of " + databaseModel, e);
        }
    }

    /** Do the handler have a read-only state? */
    public boolean isReadOnly() {
        final List<MetaDatabase> dbs = getDatabases();
        final boolean result = dbs == null || dbs.isEmpty() ? false : dbs.get(0).readOnly();
        return result;
    }

    /** Map a property to the table */
    @SuppressWarnings("unchecked")
    public void addProcedureModel(MetaProcedure metaProcedure) {
        procedureMap.put(MetaProcedure.DB_PROPERTY.of(metaProcedure).getType(), metaProcedure);
    }

    /** Map a property to the table */
    @SuppressWarnings("unchecked")
    public void addTableModel(MetaTable metaTable) {
        entityMap.put(metaTable.getType(), metaTable);
    }

    /** Map a property to the table */
    @SuppressWarnings("unchecked")
    public void addColumnModel(MetaRelation2Many column) {
        UjoProperty property = column.getProperty();
        MetaRelation2Many oldColumn = findColumnModel(property);
        if (oldColumn == null) {
            propertyMap.put(property, column);
        } else {
            final Class oldType = oldColumn.getTableClass();
            final Class newType = column.getTableClass();
            if (newType.isAssignableFrom(oldType)) {
                propertyMap.put(property, column);
            }
        }
    }

    /** Find a property annotation by the required type.
     * The property must be a public static final field in the related Ujo class.
     */
    public <T extends Annotation> T findAnnotation(UjoProperty property, Class<T> annotationClass) {
        if (!property.isDirect()) {
            property = ((CompositeProperty) property).getFirstProperty();
        }
        try {
            for (Field field : findColumnModel(property).getTableClass().getFields()) {
                if (field.getModifiers() == UjoManager.PROPERTY_MODIFIER && field.get(null) == property) {
                    return (T) field.getAnnotation(annotationClass);
                }
            }
        } catch (Throwable e) {
            throw new IllegalStateException("Illegal state for: " + property, e);
        }
        return null;
    }

    /** Find a Relation/Column model of the paramemeter property.
     * @param pathProperty Parameter can be type of Property of CompositeProperty (direct or indirect);
     * @return Returns a related model or the NULL if no model was found.
     */
    public MetaRelation2Many findColumnModel(UjoProperty pathProperty) {
        if (pathProperty != null && !pathProperty.isDirect()) {
            pathProperty = ((CompositeProperty) pathProperty).getLastProperty();
        }
        final MetaRelation2Many result = propertyMap.get(pathProperty);
        return result;
    }

    /** Find a table model by the dbClass.
     * If the table model is not found then the IllegalStateException is throwed.
     */
    public MetaTable findTableModel(Class<? extends OrmUjo> dbClass) throws IllegalStateException {
        MetaTable result = entityMap.get(dbClass);
        if (result == null) {
            final String msg = "An entity mapping bug: the " + dbClass + " is not mapped to the Database.";
            throw new IllegalStateException(msg);
        }
        return result;
    }

    /** Find a procedure model by the procedureClass.
     * If the procedure model is not found then the IllegalStateException is throwed.
     */
    public MetaProcedure findProcedureModel(Class<? extends DbProcedure> procedureClass) throws IllegalStateException {
        MetaProcedure result = procedureMap.get(procedureClass);
        if (result == null) {
            final String msg = "An procedure mapping bug: the " + procedureClass + " is not mapped to the Database.";
            throw new IllegalStateException(msg);
        }
        return result;
    }

    /** Returns parameters */
    public MetaParams getParameters() {
        return MetaRoot.PARAMETERS.of(databases);
    }

    /** Returns true, if a database meta-model is loaded. */
    public boolean isDatabaseLoaded() {
        int itemCount = MetaRoot.DATABASES.getItemCount(databases);
        return itemCount > 0;
    }

    /** Returns all database */
    public List<MetaDatabase> getDatabases() {
        return MetaRoot.DATABASES.of(databases);
    }

    /** Find all <strong>persistent<strong> properties with the required type or subtype.
     * @param type The parameter value Object.clas returns all persistent properties.
     */
    public List<UjoProperty> findPropertiesByType(Class type) {
        List<UjoProperty> result = new ArrayList<UjoProperty>();
        for (UjoProperty p : propertyMap.keySet()) {
            if (p.isTypeOf(type)) {
                result.add(p);
            }
        }
        return result;
    }

    /** Returns a final meta-model in the XML format */
    public String getConfig() {
        return databases.toString();
    }
}
