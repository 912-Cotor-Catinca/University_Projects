#include "EthnographicPark.h"
#include <QtWidgets/QApplication>
#include "Service.h"

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    Repository r;
    Service s{ r };
    vector<EthnographicPark*> wd;
    qsrand(time(0));
    for (auto i : s.getEthnologists())
        wd.push_back(new EthnographicPark{ s, i });
    for (auto w : wd)
    {
        
        QPalette* pal = new QPalette{};
        pal->setColor(QPalette::Background, QColor(qrand() % 255, qrand() % 255, qrand() % 255));
        //QWidget::setPalette(*pal);
        w->setPalette(*pal);
        w->show();
    }
        
    return a.exec();
}
