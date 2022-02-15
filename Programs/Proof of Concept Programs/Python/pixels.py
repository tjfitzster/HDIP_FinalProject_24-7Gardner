from sense_hat import SenseHat

import time


sense = SenseHat()

p = [204, 0, 204]
g = [0, 102, 102]
w = [200, 200, 200]
y = [204, 204, 0]
e = [0, 0, 0]

pet1 = [
    e, e, e, e, e, e, e, e,
    p, e, e, e, e, e, e, e,
    e, p, e, e, p, e, p, e,
    e, p, g, g, p, y, y, e,
    e, g, g, g, y, w, y, g,
    e, g, g, g, g, y, y, e,
    e, g, e, g, e, g, e, e,
    e, e, e, e, e, e, e, e
    ]

#sense.get_pixels(pet1)

#sense.show_message("Hello Everyone")
