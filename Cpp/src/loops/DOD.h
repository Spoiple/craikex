#ifndef EXJ_DOD_DOD_H
#define EXJ_DOD_DOD_H

#include "../Components.h"

namespace exj {

    class DOD {
    public:
    private:
        float width, height;
        int numberOfEntities;
        exj::Position *positions;
        exj::Velocity *velocities;
        exj::Collision *collisions;
        exj::Size *sizes;
        exj::Color *colors;
        sf::VertexArray vertexArray;
        sf::RenderWindow *window;

    public:
        explicit DOD(sf::RenderWindow *window, int numberOfEntities, int width, int height);
        void update(float dt);
        void render();
    private:
        void init();
        void movementSystem(float dt);
        void collisionSystem();
        void collisionHandlingSystem();
    };

} // exj

#endif //EXJ_DOD_DOD_H
