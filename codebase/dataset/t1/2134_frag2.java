            long time = System.currentTimeMillis();

            long rand = 0;

            if (secure) {

                rand = mySecureRand.nextLong();

            } else {

                rand = myRand.nextLong();

            }

            sbValueBeforeMD5.append(s_id);

            sbValueBeforeMD5.append(":");

            sbValueBeforeMD5.append(Long.toString(time));

            sbValueBeforeMD5.append(":");

            sbValueBeforeMD5.append(Long.toString(rand));

            valueBeforeMD5 = sbValueBeforeMD5.toString();

            md5.update(valueBeforeMD5.getBytes());

            byte[] array = md5.digest();
