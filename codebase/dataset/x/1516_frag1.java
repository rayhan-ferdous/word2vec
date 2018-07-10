    public Contact getContact(String id) {

        boolean notFound = true;

        Contact theContact = new Contact();

        Iterator i = listContact.iterator();

        while (i.hasNext() && notFound) {

            theContact = (Contact) i.next();

            if (theContact.getId().equalsIgnoreCase(id)) {

                notFound = false;

            }

        }

        if (notFound) return null; else {

            return theContact;

        }

    }
