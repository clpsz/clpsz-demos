# usage

https://codecov.io/gh/clpsz/try_codcov

Https://travis-ci.org/clpsz/try_codcov


# status

[![Build Status](https://travis-ci.org/clpsz/try_codcov.svg?branch=master)](https://travis-ci.org/clpsz/try_codcov)
[![codecov](https://codecov.io/gh/clpsz/try_codcov/branch/master/graph/badge.svg)](https://codecov.io/gh/clpsz/try_codcov)


# pypi upload

1. register https://pypi.python.org/pypi
2. prepare project
3. add ~/.pypirc
```
[distutils]
index-servers =
    pypi
    test

[pypi]
username: ****
password: ****

[test]
username: ****
password: ****
```
4. python setup.py sdist upload -r pypi
