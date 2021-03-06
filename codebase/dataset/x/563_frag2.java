    @Override

    public GeoPosition pixelToGeo(Point2D pixelCoordinate, int zoom) {

        Point2D p = ((SwegisTileProviderInfo) getInfo()).pixelCoordToMapCoord(zoom, pixelCoordinate.getX(), pixelCoordinate.getY());

        ProjCoords pc = new ProjCoords(p.getX(), p.getY());

        GeoCoords gc = null;

        try {

            gc = pl.localToGeo(pc);

        } catch (MethodNotSupportedException ex) {

            ex.printStackTrace();

        }

        return new GeoPosition(GeoCoords.radToDec(gc.m_dLatitude), GeoCoords.radToDec(gc.m_dLongitude));

    }
