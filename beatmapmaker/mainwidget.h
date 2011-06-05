#ifndef MAINWIDGET_H
#define MAINWIDGET_H

#include <QWidget>
#include <phonon>
namespace Ui {
    class MainWidget;
}

class MainWidget : public QWidget
{
    Q_OBJECT

public:
    explicit MainWidget(QWidget *parent = 0);
    ~MainWidget();

protected:
    virtual void mousePressEvent(QMouseEvent *);

private slots:
    void on_openBtn_clicked();

    void on_startBtn_clicked();

    void on_saveBtn_clicked();

private:
    Ui::MainWidget *ui;
    Phonon::AudioOutput* m_audioOut;
    Phonon::MediaObject* m_media;
    QVector<long>     m_beats;
};

#endif // MAINWIDGET_H
