            for (final Untergruppe untergruppe : firstHauptgruppe.getUntergruppen()) {

                writer.startElement("option", this);

                writer.writeAttribute("value", untergruppe.getId(), null);

                if (untergruppe.equals(selectedUntergruppe)) {

                    writer.writeAttribute("selected", "selected", null);

                }

                writer.writeText(untergruppe.getUntergruppeName(), null);

                writer.endElement("option");

            }

        }

        writer.endElement("select");

        this.renderTableFooter(writer);

    }
