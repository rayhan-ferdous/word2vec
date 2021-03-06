    private void _setYRange(double min, double max) {

        if (min > max) {

            min = -1.0;

            max = 1.0;

        } else if (min == max) {

            min -= 0.1;

            max += 0.1;

        }

        _yMin = min;

        _yMax = max;

        double largest = Math.max(Math.abs(_yMin), Math.abs(_yMax));

        _yExp = (int) Math.floor(Math.log(largest) * _LOG10SCALE);

        if (_yExp > 1 || _yExp < -1) {

            double ys = 1.0 / Math.pow(10.0, (double) _yExp);

            _ytickMin = _yMin * ys;

            _ytickMax = _yMax * ys;

        } else {

            _ytickMin = _yMin;

            _ytickMax = _yMax;

            _yExp = 0;

        }

    }
