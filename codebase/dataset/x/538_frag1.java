    public void onShareAdded(int id) {

        BluetoothOppShareInfo info = mBatch.getPendingShare();

        if (info.mDirection == BluetoothShare.DIRECTION_INBOUND) {

            mCurrentShare = mBatch.getPendingShare();

            if (mCurrentShare != null && mCurrentShare.mConfirm == BluetoothShare.USER_CONFIRMATION_AUTO_CONFIRMED) {

                if (V) Log.v(TAG, "Transfer continue session for info " + mCurrentShare.mId + " from batch " + mBatch.mId);

                processCurrentShare();

                setConfirmed();
