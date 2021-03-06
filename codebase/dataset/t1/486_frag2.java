    public void removeMember(AsteriskQueueMemberImpl member) {

        synchronized (members) {

            if (!members.containsValue(member)) {

                return;

            }

            logger.info("Remove member from the queue " + getName() + ": " + member.toString());

            members.remove(member.getLocation());

        }

        fireMemberRemoved(member);

    }
