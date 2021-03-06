            out.write("\r\n");

            out.write("            function init() {\r\n");

            out.write("                format=\"image/png\";\r\n");

            out.write("                var bounds = new OpenLayers.Bounds(\r\n");

            out.write("                617817.75, 3047534.75,\r\n");

            out.write("                650331.75, 3077605.75\r\n");

            out.write("            );\r\n");

            out.write("                var options = {\r\n");

            out.write("                    controls: [],\r\n");

            out.write("                    maxExtent: bounds,\r\n");

            out.write("                    maxResolution: 127.0078125,\r\n");

            out.write("                    projection: \"EPSG:3857\",\r\n");

            out.write("                    units: 'm'\r\n");

            out.write("                };\t\t\t\r\n");

            out.write("                map = new OpenLayers.Map('map', options);\r\n");

            out.write("                // setup tiled layer\r\n");

            out.write("                var tiled = new OpenLayers.Layer.WMS(\r\n");

            out.write("                \"cite:ktm_roads01 - Tiled\", \"http://localhost:8080/geoserver/cite/wms\",\r\n");

            out.write("                {\r\n");

            out.write("                    LAYERS: 'cite:ktm_roads01',\r\n");

            out.write("                    STYLES: '',\r\n");

            out.write("                    format: format,\r\n");

            out.write("                    tilesOrigin : map.maxExtent.left + ',' + map.maxExtent.bottom\r\n");

            out.write("                },\r\n");

            out.write("                {\r\n");

            out.write("                    buffer: 0,\r\n");

            out.write("                    displayOutsideMaxExtent: true,\r\n");

            out.write("                    isBaseLayer: true\r\n");

            out.write("                } \r\n");

            out.write("            );\t\r\n");

            out.write("\r\n");

            out.write("\r\n");

            out.write("                // create and add layers to the map\t\t\t\t\r\n");

            out.write("                start = new OpenLayers.Layer.Vector(\"Start point\", {style: start_style});\r\n");

            out.write("                stop = new OpenLayers.Layer.Vector(\"End point\", {style: stop_style});\r\n");

            out.write("                downtown = new OpenLayers.Layer.Vector(\"Downtown data area\",\r\n");

            out.write("                {style: result_style});\r\n");

            out.write("                result = new OpenLayers.Layer.Vector(\"Routing results\",\r\n");

            out.write("                {style: result_style});\r\n");

            out.write("\r\n");

            out.write("                // controls\r\n");

            out.write("                map.addControl(new OpenLayers.Control.LayerSwitcher());\r\n");

            out.write("                map.addControl(new OpenLayers.Control.MousePosition()); \r\n");
