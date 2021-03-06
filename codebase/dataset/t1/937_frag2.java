    private static double svm_svr_probability(svm_problem prob, svm_parameter param) {

        int i;

        int nr_fold = 5;

        double[] ymv = new double[prob.l];

        double mae = 0;

        svm_parameter newparam = (svm_parameter) param.clone();

        newparam.probability = 0;

        svm_cross_validation(prob, newparam, nr_fold, ymv);

        for (i = 0; i < prob.l; i++) {

            ymv[i] = prob.y[i] - ymv[i];

            mae += Math.abs(ymv[i]);

        }

        mae /= prob.l;

        double std = Math.sqrt(2 * mae * mae);

        int count = 0;

        mae = 0;

        for (i = 0; i < prob.l; i++) if (Math.abs(ymv[i]) > 5 * std) count = count + 1; else mae += Math.abs(ymv[i]);
