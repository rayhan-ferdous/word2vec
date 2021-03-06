    void retrieveUserPresenceAsync(final AsyncCompletion completion) {

        Primitive request = new Primitive(ImpsTags.GetPresence_Request);

        request.addElement(this.getSession().getLoginUserAddress().toPrimitiveElement());

        AsyncTransaction tx = new AsyncTransaction(mTransactionManager) {



            @Override

            public void onResponseOk(Primitive response) {

                PrimitiveElement presence = response.getElement(ImpsTags.Presence);

                PrimitiveElement presenceSubList = presence.getChild(ImpsTags.PresenceSubList);

                mUserPresence = ImpsPresenceUtils.extractPresence(presenceSubList, mConfig.getPresenceMapping());

                if (mUserPresence.getStatus() == Presence.OFFLINE) {

                    mUserPresence.setStatus(Presence.AVAILABLE);

                }

                compareAndUpdateClientInfo();

            }



            @Override

            public void onResponseError(ImpsErrorInfo error) {

                mUserPresence = new Presence(Presence.AVAILABLE, "", null, null, Presence.CLIENT_TYPE_MOBILE, ImpsUtils.getClientInfo());

                completion.onError(error);

            }



            private void compareAndUpdateClientInfo() {

                if (!ImpsUtils.getClientInfo().equals(mUserPresence.getExtendedInfo())) {

                    updateClientInfoAsync(completion);

                    return;

                }

                completion.onComplete();

            }

        };

        tx.sendRequest(request);

    }
