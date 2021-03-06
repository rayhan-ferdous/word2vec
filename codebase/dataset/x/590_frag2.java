    void doOnClickListener(AdapterView<?> arg0, View view, int index, long arg3) {

        Intent lIntent = new Intent();

        switch(index) {

            case 0:

                if (isStarted) {

                    lIntent.setClass(Enforcement.this, SearchInMapActivity.class);

                    Enforcement.this.startActivity(lIntent);

                }

                break;

            case 1:

                if (isStarted) {

                    lIntent.setClass(Enforcement.this, FieldSearchActivity.class);

                    Enforcement.this.startActivity(lIntent);

                }

                break;

            case 2:

                if (isStarted) {

                    lIntent.setClass(Enforcement.this, CheckTypeActivity.class);

                    Enforcement.this.startActivity(lIntent);

                }

                break;

            case 3:

                Bundle lBundle = new Bundle();

                if (image[3] == R.drawable.homenext1) {

                    lBundle.putInt("RESULTCODE", 1);

                    isStarted = true;

                } else {

                    lBundle.putInt("RESULTCODE", 2);

                    isStarted = false;

                }

                lIntent.putExtras(lBundle);

                lIntent.setClass(Enforcement.this, AttendanceActivity.class);

                Enforcement.this.startActivityForResult(lIntent, lBundle.getInt("RESULTCODE"));

                break;

            case 4:

                lIntent.setClass(Enforcement.this, ListTodayCheckActivity.class);

                Enforcement.this.startActivity(lIntent);

                break;

            case 5:

                if (Enforcement.mCurrentChangsuo != null) {

                    lIntent.setClass(Enforcement.this, DailyCheckResultActivity.class);

                    lIntent.putExtra("selectedcs", Enforcement.mCurrentChangsuo);

                    Enforcement.this.startActivity(lIntent);

                }

                break;

            case 6:

                lIntent.setClass(Enforcement.this, TodayAndDraftActivity.class);

                Enforcement.this.startActivity(lIntent);

                break;

            case 7:

                lIntent.setClass(Enforcement.this, SystemSettingActivity.class);

                Enforcement.this.startActivity(lIntent);

                break;

            case 8:

                bundle.putBoolean("isClear", false);

                lIntent.setClass(Enforcement.this, SxryMActivity.class);

                lIntent.putExtras(bundle);

                Enforcement.this.startActivity(lIntent);

                break;

            case 9:

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:057185022377"));

                startActivity(intent);

                break;

            default:

                break;

        }

    }
