        if ("creationDate".equals(pColumn)) return vo.getCreationDate();

        if ("dueDate".equals(pColumn)) return vo.getDueDate();

        if ("bankAccountID".equals(pColumn)) return new Long(vo.getBankAccountID());
