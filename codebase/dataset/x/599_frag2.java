    public Template resolve(TemplateEngine engine, String name, Locale locale, String encoding) throws TemplateException {

        Template template = null;

        try {

            try {

                String ownName = getKey().getName();

                int index = ownName.lastIndexOf('/');

                String relativeName = ((index >= 0) ? ownName.substring(0, index + 1) : "") + name;

                template = engine.getTemplate(relativeName, locale, encoding);

            } finally {

                if (template == null) template = engine.getTemplate(name, locale, encoding);

            }

        } catch (TemplateException e) {

            throw new TemplateResolverException("Could ont load template \"" + name + "\"", e);

        }

        return template;

    }
