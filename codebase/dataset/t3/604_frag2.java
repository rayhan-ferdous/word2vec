        private void resetKey() {

            try {

                cipher.reset();

                cipher.init(Collections.singletonMap(IBlockCipher.KEY_MATERIAL, key));

            } catch (InvalidKeyException ike) {

                throw new Error(ike);

            } catch (IllegalArgumentException iae) {

                throw new Error(iae);

            }

        }
