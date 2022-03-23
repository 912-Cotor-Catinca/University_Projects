import pickle


def load_environment(environment, numfile):
    with open(numfile, "rb") as f:
        dummy = pickle.load(f)
        environment.set_environment(dummy)
        f.close()


def saveEnvironment(self, numFile):
    with open(numFile, 'wb') as f:
        pickle.dump(self, f)
        f.close()
