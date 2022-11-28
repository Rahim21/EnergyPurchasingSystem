from subprocess import Popen, PIPE
import subprocess
from threading import Thread, Lock
import tkinter as tk
import platform
import webbrowser


class TkinterPopen(tk.Text):
    def __init__(self, master, state="disabled", **kwargs):
        super().__init__(master, state=state, **kwargs)
        self.commands = []
        self.proc = None
        self.running = True
        self.stdout_buffer = ""
        self.stdout_buffer_lock = Lock()

    def stdout_loop(self, last_loop: bool = False) -> None:
        with self.stdout_buffer_lock:
            # Get the data and clear the buffer:
            data, self.stdout_buffer = self.stdout_buffer, ""
        state = super().cget("state")
        super().config(state="normal")
        super().insert("end", data)
        super().see("end")
        super().config(state=state)
        if self.proc is None:
            if len(self.commands) == 0:
                # If we are done with all of the commands:
                if last_loop:
                    return None
                super().after(100, self.stdout_loop, True)
            else:
                # If we have more commands to do call `start_next_proc`
                self.start_next_proc()
        else:
            super().after(100, self.stdout_loop)

    def start_next_proc(self) -> None:
        command = self.commands.pop(0)  # Take the first one from the list
        self.proc = Popen(command, stdout=PIPE)
        new_thread = Thread(target=self.read_stdout, daemon=True)
        new_thread.start()
        self.stdout_loop()

    def run_commands(self, commands: list) -> None:
        self.commands = commands
        self.start_next_proc()

    def read_stdout(self):
        while self.proc is not None and self.proc.poll() is None:
            self._read_stdout()
        self._read_stdout()
        self.proc = None

    def _read_stdout(self) -> None:
        if self.proc is not None and self.proc.stdout is not None:
            line = self.proc.stdout.readline()
            with self.stdout_buffer_lock:
                self.stdout_buffer += line.decode()


if __name__ == "__main__":
    def run_web():
        global serveurWebBool
        # php -S localhost:8000 -t ServeurWebRevendeur/public
        command = ["php", "-S", "localhost:8000",
                   "-t", "ServeurWebRevendeur/public"]
        tkinter_popenWeb.run_commands([command])
        serveurWebBool = True
        updateLabelWeb()

    def open_web():
        if((platform.system() == "Windows")):
            webbrowser.open("http://localhost:8000")
        else:
            subprocess.call(["open", "http://localhost:8000"])

    def stop_web():
        if(platform.system() == "Windows"):
            subprocess.call(["taskkill", "/F", "/IM", "php.exe"])
        else:
            global serveurWebBool
            if tkinter_popenWeb.proc is not None:
                print('----- Arrêt du site du revendeur -----')
                tkinter_popenWeb.proc.terminate()
        serveurWebBool = False
        updateLabelWeb()

    def kill_web():
        global serveurWebBool
        print('----- !!! Arrêt total !!! du site du revendeur -----')
        subprocess.call(["killall", "php"])
        serveurWebBool = False
        updateLabelWeb()

    def compile_server():
        print('----- Compilation des serveurs -----')
        # Compilation des serveurs depuis le dossier courant : Projet_Info0503_HAYAT_MTARFI
        command = ["javac", "-d", "Serveurs/cls/", "-cp", "Serveurs/lib/json-20220924.jar",
                   "-sourcepath", "Serveurs/src/", "Serveurs/src/Lanceur.java"]
        tkinter_popenServeur.run_commands([command])

    def run_server():
        print('----- Lancement des serveurs -----')
        # Lancement des serveurs depuis le dossier courant : Projet_Info0503_HAYAT_MTARFI
        print('mon systeme est :'+platform.system())
        if((platform.system() == "Windows")):
            command = ["java", "-cp", "Serveurs/cls/;Serveurs/lib/json-20220924.jar", "Lanceur", "Serveurs/config.json"]
        else:
            print('je suis sur mac')
            command = ["java", "-cp", "Serveurs/cls/:Serveurs/lib/json-20220924.jar",
                       "Lanceur", "Serveurs/config.json"]
        tkinter_popenServeur.run_commands([command])

    def stop_server():
        if((platform.system() == "Windows")):
            print('----- Arrêt des serveurs -----')
            subprocess.call(["taskkill", "/F", "/IM", "java.exe"])
        else:
            print('----- Les serveurs sont arrêtés -----')
            command = ["killall", "java"]
            tkinter_popenServeur.run_commands([command])

    def updateLabelWeb():
        if serveurWebBool:
            labelWeb.config(text="Serveur Web : ON", background="green")
        else:
            labelWeb.config(text="Serveur Web : OFF", background="red")

    root = tk.Tk()
    root.title("HAYAT|MTARFI - [SALE] Système d'Achat de L'Energie")
    root.resizable(width=False, height=False)

    tkinter_popenWeb = TkinterPopen(root)
    # tkinter_popenWeb.grid(column=0, row=0, columnspan=2)
    # créer un label avec background vert si serveur web en marche et rouge sinon
    labelWeb = tk.Label(root, text="Serveur Web : OFF", bg="red")
    serveurWebBool = False
    labelWeb.grid(column=0, row=0)

    tkinter_popenServeur = TkinterPopen(root)
    tkinter_popenServeur.grid(column=1, row=0, columnspan=2)

    button = tk.Button(
        root, text="Lancer le site du Revendeur", command=run_web)
    button.grid(column=0, row=1, columnspan=1)

    button = tk.Button(
        root, text="Ouvrir la page du site", command=open_web)
    button.grid(column=0, row=2, columnspan=1)

    button = tk.Button(
        root, text="Arrêter le serveur du site", command=stop_web)
    button.grid(column=0, row=3, columnspan=1)

    button = tk.Button(
        root, text="Tuer le serveur du site", command=kill_web)
    button.grid(column=0, row=4, columnspan=1)

    button = tk.Button(root, text="Compiler les serveurs",
                       command=compile_server)
    button.grid(column=1, row=1, columnspan=2)

    button = tk.Button(root, text="Lancer les serveurs", command=run_server)
    button.grid(column=1, row=2, columnspan=2)

    button = tk.Button(root, text="Fermer les serveurs", command=stop_server)
    button.grid(column=1, row=3, columnspan=2)

    root.mainloop()
