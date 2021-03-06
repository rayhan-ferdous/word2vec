    public void insertIntervaloChegada(Integer codemp, Integer codfilial, Date dataatendo, Date dataatendofin, String horaini, String horafim, Integer codempae, Integer codfilialae, Integer codatend, Integer codempus, Integer codfilialus, String idusu) throws SQLException {

        Atendimento intervalo = loadModelAtend(codemp, codfilial, (Integer) prefs[PREFS.CODEMPME.ordinal()], (Integer) prefs[PREFS.CODFILIALME.ordinal()], (Integer) prefs[PREFS.CODMODELME.ordinal()]);

        intervalo.setDataatendo(dataatendo);

        intervalo.setDataatendofin(dataatendofin);

        intervalo.setHoraatendo(horaini);

        intervalo.setHoraatendofin(horafim);

        intervalo.setCodempae(codempae);

        intervalo.setCodfilialae(codfilialae);

        intervalo.setCodatend(codatend);

        intervalo.setCodempus(codempus);

        intervalo.setCodfilialus(codfilialus);

        intervalo.setIdusu(idusu);

        insert(intervalo);

    }
