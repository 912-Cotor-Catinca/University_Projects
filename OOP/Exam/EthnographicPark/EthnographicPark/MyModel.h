#pragma once
#include "Service.h"
#include "Ethonologist.h"
#include <QAbstractTableModel>
class MyModel :
    public QAbstractTableModel
{
private:
    Service& s;
    Ethonologist& e;
public:
    MyModel(Service& s, Ethonologist& e ,QObject* parent = NULL);
    int rowCount(const QModelIndex& parent = QModelIndex()) const override;
    int columnCount(const QModelIndex& parent = QModelIndex()) const override;
    //  Value  at a given  position
    QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override;
    QVariant headerData(int section, Qt::Orientation orientation, int role = Qt::DisplayRole) const override;
    //will be called when a cellis edited
    bool setData(const QModelIndex& index, const QVariant& value, int role) override;
    Qt::ItemFlags flags(const QModelIndex& index) const override;
};

