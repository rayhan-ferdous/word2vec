                if (channel.getDocs() != null) {

                    Text content = doc.createTextNode(channel.getDocs());

                    Element elem = doc.createElement("docs");

                    elem.appendChild(content);

                    chan.appendChild(elem);

                }

                if (channel.getCloud() != null) {

                    Channel.Cloud cloud = channel.getCloud();

                    Element elem = doc.createElement("cloud");

                    elem.setAttribute("domain", cloud.getDomain());

                    elem.setAttribute("port", String.valueOf(cloud.getPort()));

                    elem.setAttribute("path", cloud.getPath());

                    elem.setAttribute("registerProcedure", cloud.getRegisterProcedure());
