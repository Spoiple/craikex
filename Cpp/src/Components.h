#ifndef EXJ_DOD_COMPONENTS_H
#define EXJ_DOD_COMPONENTS_H

#include <SFML/Graphics.hpp>

namespace exj {

    struct Position {
        float x, y;
    };

    struct Color {
        float r, g, b;
    };

    struct Velocity {
        float x, y;
    };

    struct Size {
        float width, height;
    };

    struct Collision {
        bool hasCollided = false;
    };

} // exj

#endif //EXJ_DOD_COMPONENTS_H
