#include "Tutorials.h"
#include <QtWidgets/QApplication>
#include "GUI.h"
#include <qpalette.h>

int main(int argc, char* argv[])
{
    QApplication a(argc, argv);
    Service s;
    ServiceUser su;
    GUI gui{ s, su };

    gui.showMaximized();
    return a.exec();
}