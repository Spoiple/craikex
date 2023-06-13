#ifndef EXJ_DOD_OODPOINTER_H
#define EXJ_DOD_OODPOINTER_H


#include "SFML/Graphics/VertexArray.hpp"
#include "../Rectangle.h"

namespace exj {
    class OODPointer {
    public:
    private:
        float width, height;
        int numberOfEntities;
        exj::Rectangle **entities;
        sf::VertexArray vertexArray;
        sf::RenderWindow *window;

    public:
        explicit OODPointer(sf::RenderWindow
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


#endif //EXJ_DOD_OODPOINTER_H
