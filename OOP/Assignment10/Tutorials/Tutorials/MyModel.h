#pragma once
#include <qabstractitemmodel.h>
#include "ServiceUser.h"
class MyModel :
    public QAbstractTableModel
{
public:
    MyModel(ServiceUser& rep, QObject* parent = NULL);
    int rowCount(const QModelIndex& parent = QModelIndex()) const override;
    int columnCount(const QModelIndex& parent = QModelIndex()) const override;
    QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override;
    bool setData(const QModelIndex& index, const QVariant& value, int role) override;
    Qt::ItemFlags flags(const QModelIndex& index) const override;
private:
    ServiceUser& r;
};

