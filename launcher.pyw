import subprocess
import threading
import multiprocessing
import time
import sys
import signal

def phpServRestart(process, stop):
    try:
        for i in range(5):
            if stop.is_set():
                break
            if i == 4:
                process.kill()
                break
            time.sleep(1)
    except KeyboardInterrupt:
        pass

def phpServ():
    try:
        process = subprocess.Popen(['php', '-S', '0.0.0.0:80', '-t', 'LocalMail/'], stdout=subprocess.PIPE, stderr=subprocess.STDOUT, text=True)
        stop = multiprocessing.Event()
        shutdownProcess = threading.Thread(target=phpServRestart, args=(process, stop,))
        for line in iter(process.stdout.readline, ''):
            line = line.rstrip()
            
            if line.endswith("Accepted"):
                if shutdownProcess.is_alive():
                    stop.set()
                    shutdownProcess.join()
                stop = multiprocessing.Event()
                shutdownProcess = threading.Thread(target=phpServRestart, args=(process, stop,))
                shutdownProcess.start()
            else:
                stop.set()
        process.wait(1)
        threading.Thread(target=phpServ).start()
    except KeyboardInterrupt:
        process.kill()
        sys.exit()

def javaServ():
    try:
        javaProcess = subprocess.Popen(['java', '-jar', 'server/LocalMail.jar'], stdout=subprocess.PIPE, stderr=subprocess.STDOUT, text=True)
        while(javaProcess.poll() == None):
            continue
    except KeyboardInterrupt:
        javaProcess.kill();
        sys.exit()
        return

php = None
java = None

if __name__ == '__main__':
    php = multiprocessing.Process(target=phpServ)
    php.start()
    java = multiprocessing.Process(target=javaServ)
    java.start()
    try:
        while True:
            time.sleep(1)
    except KeyboardInterrupt:
        php.terminate()
        java.terminate()
        pass

def signal_handler(signal, frame):
    if(php is not None):
        php.terminate()
    if(java is not None):
        java.terminate()
    sys.exit(0)

signal.signal(signal.SIGINT, signal_handler)