    private static void linkOrphanedBonesToNewBone(E3DActor actor, E3DBone possibleParentBone, E3DHashListMap orphanedBones) {

        if (orphanedBones.containsKey(possibleParentBone.getBoneID())) {

            ArrayList boneList = (ArrayList) orphanedBones.get(possibleParentBone.getBoneID());

            for (int i = 0; i < boneList.size(); i++) {

                E3DBone childBone = (E3DBone) boneList.get(i);

                actor.getSkeleton().addBone(childBone, possibleParentBone.getBoneID());

                linkOrphanedBonesToNewBone(actor, childBone, orphanedBones);

            }

            orphanedBones.remove(possibleParentBone.getBoneID());

        }

    }
