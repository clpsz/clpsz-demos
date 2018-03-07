import unittest
import clpszawesome


class TestMethods(unittest.TestCase):
    def test_smile(self):
        self.assertEqual(clpszawesome.smile(), ":)")

    def test_frown(self):
        self.assertEqual(clpszawesome.frown(), ":(")


if __name__ == '__main__':
    unittest.main()

