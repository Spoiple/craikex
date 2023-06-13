#include "Rectangle.h"

namespace exj {
    Rectangle::Rectangle() {

    }

    Rectangle::Rectangle(float xPos, float yPos, float width, float height, float xVel, float yVel, bool collided, float r,
                         float g, float b) : xPos_(xPos), yPos_(yPos), width_(width), height_(height), xVel_(xVel),
                                       yVel_(yVel), collided(collided), r(r), g(g), b(b) {}

    Rectangle::~Rectangle() {

    }

    void Rectangle::move(float dt) {
        xPos_ += xVel_ * dt;
        yPos_ += yVel_ * dt;
    }

    void Rectangle::checkCollisions(float width, float height) {
        if (xPos_ < 0) {
            xPos_ = 0;
            xVel_ = -xVel_;
            collided = true;
        } else if (xPos_ > width - width_) {
            xPos_ = width - width_;
            xVel_ = -xVel_;
            collided = true;
        }
        if (yPos_ < 0) {
            yPos_ = 0;
            yVel_ = -yVel_;
            collided = true;
        } else if (yPos_ > height - height_) {
            yPos_ = height - height_;
            yVel_ = -yVel_;
            collided = true;
        }
    }

    void Rectangle::handleCollisions() {
        if (collided) {
            collided = false;
            r = (float) rand()/RAND_MAX;
            g = (float) rand()/RAND_MAX;
            b = (float) rand()/RAND_MAX;
        }
    }


} // exj