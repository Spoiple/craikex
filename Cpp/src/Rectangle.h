#ifndef SFML_TEST_ENTITY_H
#define SFML_TEST_ENTITY_H

#include <SFML/Graphics.hpp>

namespace exj {

    class Rectangle {
    private:
    public:
        float r, g, b;
        float xPos_, yPos_;
        float xVel_, yVel_;
        float width_, height_;
        bool collided = false;

        Rectangle(/* args */);

        Rectangle(float xPos, float yPos, float width, float height, float xVel, float yVel, bool collided, float r,
                  float g, float b);

        ~Rectangle();

        void move(float dt);

        void checkCollisions(float width, float height);

        void handleCollisions();
    };

} // exj

#endif //SFML_TEST_ENTITY_H
