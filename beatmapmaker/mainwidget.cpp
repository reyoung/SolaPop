#include "mainwidget.h"
#include "ui_mainwidget.h"
#include <QtGui>
MainWidget::MainWidget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::MainWidget)
{
    ui->setupUi(this);
    this->m_audioOut = new Phonon::AudioOutput(Phonon::MusicCategory,this);
    this->m_media = new Phonon::MediaObject(this);
    Phonon::createPath(m_media,m_audioOut);
    this->connect(m_media,SIGNAL(aboutToFinish()),this,SLOT(on_startBtn_clicked()));
}

MainWidget::~MainWidget()
{
    delete ui;
}

void MainWidget::on_openBtn_clicked()
{
    QString fn = QFileDialog::getOpenFileName();
    Phonon::MediaSource ms(fn);
    this->m_media->setCurrentSource(ms);
    qDebug()<<"Open Clicked";
}

void MainWidget::on_startBtn_clicked()
{
    if(this->ui->startBtn->text()=="play"){
        m_beats.clear();
        this->m_media->play();
        this->ui->startBtn->setText("stop");
    }else{
        this->m_media->stop();
        this->m_media->clearQueue();
        this->ui->startBtn->setText("play");
    }
}

void MainWidget::mousePressEvent(QMouseEvent * ev)
{
    ev->accept();
    this->m_beats.append(this->m_media->currentTime());
    qDebug()<<"Append "<<this->m_media->currentTime();
}

void MainWidget::on_saveBtn_clicked()
{
    QString sv = QFileDialog::getSaveFileName();
    QFile file(sv);
    if(file.open(QFile::WriteOnly)){
        QTextStream tout(&file);
        foreach(long time, this->m_beats){
            tout<<time<<endl;
        }
        file.close();
    }
}
