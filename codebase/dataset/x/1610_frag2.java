    public boolean addPermissionHolder(PermissionHolder holder, boolean refresh) {

        PermissionHolderImpl holderImpl = (PermissionHolderImpl) holder;

        if (this.sleeComponentsPermissions.contains(holderImpl)) {

            return false;

        } else {

            this.sleeComponentsPermissions.add(holderImpl);

            KeyStore ks = null;

            Enumeration<PolicyParser.GrantEntry> grantEntries = holderImpl.getPolicyParser().grantElements();

            while (grantEntries.hasMoreElements()) {

                parseGrantEntry(grantEntries.nextElement(), ks, holderImpl);

            }

            if (refresh) this.refresh();

            return true;

        }

    }
