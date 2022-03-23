#include "MyModel.h"
#include <qfont.h>
#include <qbrush.h>


MyModel::MyModel(Service& s, Ethonologist& e, QObject* parent) : s{ s }, e{e}, QAbstractTableModel{ parent }
{
}

int MyModel::rowCount(const QModelIndex& parent) const
{
	return this->s.getBuildings().size() + 1;
}

int MyModel::columnCount(const QModelIndex& parent) const
{
	return 4;
}
QVariant MyModel::data(const QModelIndex& index, int role) const
{
	int row = index.row();
	int column = index.column();
	vector<Buildings> ideas = this->s.getBuildingsSorted(this->e.getArea());
	if (row == ideas.size())
		return QVariant();
	Buildings i = ideas[row];
	if (role == Qt::DisplayRole || role == Qt::EditRole)
	{
		switch (column)
		{
		case 0:
			return QString::fromStdString(to_string(i.getId()));
		case 1:
			return QString::fromStdString(i.getDescription());
		case 2:
			return QString::fromStdString(i.getArea());
		case 3:
			return QString::fromStdString(i.getCoordString());
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
		if (i.getArea() == this->e.getArea())
		{
			return QBrush{ QColor{95, 188, 245} };
		}
		else
			return QBrush{ QColor{230, 232, 235} };
	}

	return QVariant{};
}

QVariant MyModel::headerData(int section, Qt::Orientation orientation, int role) const
{
	if (role == Qt::DisplayRole)
	{
		if (orientation == Qt::Horizontal)
		{
			switch (section)
			{
			case 0:
				return QString{ "Id" };
			case 1:
				return QString{ "Description" };
			case 2:
				return QString{ "Area" };
			case 3:
				return QString{ "Coordinates" };
			default:
				break;
			}
		}
	}
	if (role == Qt::FontRole)
	{
		QFont font("Times", 10, 10, true);
		font.setItalic(false);
		return font;
	}
	return QVariant{};
}

bool MyModel::setData(const QModelIndex& index, const QVariant& value, int role)
{
	return false;
}

Qt::ItemFlags MyModel::flags(const QModelIndex& index) const
{
	return Qt::ItemFlags();
}
