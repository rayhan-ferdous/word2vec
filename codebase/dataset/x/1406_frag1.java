    private void CreateSpectrum(String _spot_id, String _job_item_id, String _peak_list_id, String _precursor_mass, String _charge) {

        double precursormass;

        int peptidecharge;

        try {

            precursormass = Double.parseDouble(_precursor_mass);

        } catch (Exception ex1) {

            precursormass = 0.0;

        }

        try {

            peptidecharge = Integer.parseInt(_charge);

        } catch (Exception ex1) {

            peptidecharge = -1;

        }

        try {

            MySpectrum = new TSpectrum(_spot_id, _job_item_id, _peak_list_id, precursormass);

            if (peptidecharge > 0) MySpectrum.SetPeptideCharge(peptidecharge);

        } catch (Exception ex2) {

            ex2.printStackTrace();

        }

    }
