        if ("tstamp".equals(pColumn)) return vo.getTstamp();

        if ("dmType".equals(pColumn)) return new Long(vo.getDmType());

        if ("dmSubType".equals(pColumn)) return new Long(vo.getDmSubType());
