        if ("ledgerID".equals(pColumn)) return new Long(vo.getLedgerID());

        if ("debit".equals(pColumn)) return new Long(vo.getDebit());

        if ("credit".equals(pColumn)) return new Long(vo.getCredit());
