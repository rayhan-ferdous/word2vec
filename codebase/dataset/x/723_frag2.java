                out.write("<script>\n");

                out.write("function executeXavaAction(confirmMessage, takesLong, formu, action) {\n");

                out.write("\texecuteXavaAction(confirmMessage, takesLong, formu, action, null);\n");

                out.write("}\n");

                out.write("function executeXavaAction(confirmMessage, takesLong, formu, action, argv) {\t\n");

                out.write("\tif (confirmMessage != \"\" && !confirm(confirmMessage)) return;\n");

                out.write("\tif (takesLong) {\n");
