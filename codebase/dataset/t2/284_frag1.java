        List<CoSingleTextCheckList3> list = coSingleTextCheckListDAO.findAll(nRowStart, nMaxResults);

        if (list.size() == 0) {

            result.setError(true);

            result.setMessage(bundle.getString("checkListStudent.list.notFound"));

        } else {

            Object[] array = { list.size() };

            result.setMessage(MessageFormat.format(bundle.getString("checkListStudent.list.success"), array));

            result.setObjResult(list);

            if ((nRowStart > 0) || (nMaxResults > 0)) result.setNumResult(coSingleTextCheckListDAO.findAll().size()); else result.setNumResult(list.size());

        }

        return result;
