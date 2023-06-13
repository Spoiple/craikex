#ifndef EXJ_DOD_OODCONT_H
#define EXJ_DOD_OODCONT_H


#include "SFML/Graphics/VertexArray.hpp"
#include "../Rectangle.h"

namespace exj {
    class OODCont {
    public:
    private:
        float width, height;
        int numberOfEntities;
        exj::Rectangle *entities;
        sf::VertexArray vertexArray;
        sf::RenderWindow *window;

    public:
        explicit OODCont(sf::RenderWindow
                             *window,
                         int numberOfEntities,
                         int width,
                         int height
        );

        void update(float dt);
        void render();
    private:
    };
} // exj


#endif //EXJ_DOD_OODCONT_H
