from controller import Controller
from gui import GUI


def main():
    service = Controller()
    ui = GUI(service)
    ui.start()


if __name__ == '__main__':
    main()


