            if ("recipientID".equals(pColumn)) return new Long(fTotal.getRecipientID());

            if ("beneficiaryID".equals(pColumn)) return new Long(fTotal.getBeneficiaryID());

            if ("esrReferenceNumber".equals(pColumn)) return fTotal.getEsrReferenceNumber();
