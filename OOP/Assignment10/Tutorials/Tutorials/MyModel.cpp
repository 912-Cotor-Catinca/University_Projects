#include "MyModel.h"
#include <qfont.h>
#include <qbrush.h>

MyModel::MyModel(ServiceUser& rep, QObject* parent) : r{rep}, QAbstractTableModel{ parent }
{
}

int MyModel::rowCount(const QModelIndex& parent) const
{
    return this->r.GetWatchList().size() + 1;
}

int MyModel::columnCount(const QModelIndex& parent) const
{
    return 5;
}

QVariant MyModel::data(const QModelIndex& index, int role) const
{
    int row = index.row();
    int column = index.column();

    vector<MasterC> tutorials = this->r.GetWatchList();
    if (row == tutorials.size())
        return QVariant();

    MasterC c = tutorials[row];
    if (role == Qt::DisplayRole || role == Qt::EditRole)
    {
        switch (column)
        {
        case 0:
            return QString::fromStdString(c.getTitle());
        case 1:
            return QString::fromStdString(c.getPresenter());
        case 2:
            return QString::fromStdString(c.getDuration());
        case 3:
            return QString::fromStdString(c.getLink());
        case 4:
            return QString::fromStdString(to_string(c.getLikes()));
        default:
            break;
        }
    }
    if (role == Qt::FontRole)
    {
        QFont font("Times", 10, 10, true);
        font.setItalic(false);
        return font;
    }
    if (role == Qt::BackgroundRole)
    {
        if (row % 2 == 1)
        {
            return QBrush{ QColor{213, 217, 222} };
        }
        else
            return QBrush{ QColor{230, 232, 235} };
    }

    return QVariant{};
}

bool MyModel::setData(const QModelIndex& index, const QVariant& value, int role)
{
    if (!index.isValid() || role != Qt::EditRole)
        return false;
    int tutorialIndex = index.row();
    vector<MasterC> tutorials = this->r.GetWatchList();
    if (tutorialIndex == tutorials.size())
    {
        this->beginInsertRows(QModelIndex{}, tutorialIndex, tutorialIndex);
        switch (index.column())
        {
        case 0:
            this->r.add(MasterC{ value.toString().toStdString(), "", "", "", 0});
            break;
        case 1:
            this->r.add(MasterC{ "", value.toString().toStdString(), "", "", 0 });
            break;
        case 2:
            this->r.add(MasterC{ "",  "",value.toString().toStdString(), "", 0 });
            break;
        case 3:
            this->r.add(MasterC{ "",  "", "",value.toString().toStdString(), 0 });
            break;
        case 4:
            this->r.add(MasterC{ "",  "", "", "", stoi(value.toString().toStdString()) });
            break;
        }
        this->endInsertRows();
        return true;
    }
    MasterC& c = tutorials[tutorialIndex];
    switch (index.column())
    {
    case 0:
        c.setTitle(value.toString().toStdString());
    case 1:
        c.setPres(value.toString().toStdString());
    case 2:
        c.setDuration(value.toString().toStdString());
    case 3:
        c.setLink(value.toString().toStdString());
    case 4:
        c.setLikes(stoi(value.toString().toStdString()));
    }
    this->r.updateTutorial(tutorialIndex, c);
    
    emit dataChanged(index, index);

    return true;
}

Qt::ItemFlags MyModel::flags(const QModelIndex& index) const
{
    return Qt::ItemIsSelectable | Qt::ItemIsEditable | Qt::ItemIsEnabled;
}
