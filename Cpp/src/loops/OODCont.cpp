#include "OODCont.h"

namespace exj {

    OODCont::OODCont(sf::RenderWindow *window, int numberOfEntities, int width, int height) {
        this->window = window;
        this->width = width;
        this->height = height;
        vertexArray = sf::VertexArray(sf::Quads);
        vertexArray.resize(numberOfEntities * 4);
        this->numberOfEntities = numberOfEntities;
        entities = new exj::Rectangle[numberOfEntities];
        for (int i = 0; i < numberOfEntities; i++) {
            entities[i] = exj::Rectangle{static_cast<float>(rand() % width), static_cast<float>(rand() % height), 10, 10, static_cast<float>((rand() % 160) - 80), static_cast<float>((rand() % 160) - 80), false, static_cast<float>(rand() % 255), static_cast<float>(rand() % 255), static_cast<float>(rand() % 255)};
        }
    }

    void OODCont::update(float dt) {
        for (int i = 0; i < numberOfEntities; i++) {
            entities[i].move(dt);
        }
        for (int i = 0; i < numberOfEntities; i++) {
            entities[i].checkCollisions(width, height);
        }
        for (int i = 0; i < numberOfEntities; i++) {
            entities[i].handleCollisions();
        }
    }

    void OODCont::render() {
        for (int i = 0; i < numberOfEntities; i++) {
            sf::Vertex *quad = &vertexArray[i * 4];
            quad[0].position = sf::Vector2f(entities[i].xPos_, entities[i].yPos_);
            quad[1].position = sf::Vector2f(entities[i].xPos_ + entities[i].width_, entities[i].yPos_);
            quad[2].position = sf::Vector2f(entities[i].xPos_ + entities[i].width_, entities[i].yPos_ + entities[i].height_);
            quad[3].position = sf::Vector2f(entities[i].xPos_, entities[i].yPos_ + entities[i].height_);

            quad[0].color = quad[1].color = quad[2].color = quad[3].color = {static_cast<sf::Uint8>(0xFF * entities[i].r), static_cast<sf::Uint8>(0xFF * entities[i].g), static_cast<sf::Uint8>(0xFF * entities[i].b)};
        }
        window->clear();
        window->draw(vertexArray);
        window->display();
    }

} // exj
#include "OODCont.h"
