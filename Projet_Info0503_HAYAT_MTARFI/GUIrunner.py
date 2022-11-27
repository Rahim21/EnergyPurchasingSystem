import tkinter as tk
import os
import sys
import subprocess


class StdoutRedirector(object):  # use flush() to force output to be written
    def __init__(self, text_widget):
        self.text_space = text_widget

    def write(self, str):
        self.text_space.insert(tk.END, str)
        self.text_space.see(tk.END)
        self.text_space.update_idletasks()


class CoreGUI(object):
    def __init__(self, parent):
        self.parent = parent
        self.InitUI()
        button = tk.Button(self.parent, text="START", command=self.start)
        button.grid(column=0, row=1, columnspan=2)

        button = tk.Button(self.parent, text="STOP", command=self.stop)
        button.grid(column=0, row=2, columnspan=2)

        button = tk.Button(self.parent, text="KILL", command=self.kill)
        button.grid(column=0, row=3, columnspan=2)

    def start(self):
        print('START!')
        global p
        p = subprocess.Popen(["php", "-S", "localhost:8000",
                              "-t", "ServeurWebRevendeur/public"], stdout=subprocess.PIPE, stderr=subprocess.PIPE, universal_newlines=True)
        # # cd Serveurs && java -cp cls/:lib/json-20220924.jar Lanceur config.json
        # p = subprocess.Popen(["cd", "Serveurs", "&&", "java", "-cp", "cls/:lib/json-20220924.jar", "Lanceur",
        #                      "config.json"], stdout=subprocess.PIPE, stderr=subprocess.PIPE, universal_newlines=True)

    def stop(self):
        print('stop')
        global p
        p.terminate()

    def kill(self):
        print('kill')
        subprocess.call(["killall", "php"])

    def InitUI(self):
        self.text_box = tk.Text(self.parent, wrap='word', height=11, width=50)
        self.text_box.grid(column=0, row=0, columnspan=2,
                           sticky='NSWE', padx=5, pady=5)
        sys.stdout = StdoutRedirector(self.text_box)


root = tk.Tk()
root.title("Serveur Web Revendeur")
gui = CoreGUI(root)
root.mainloop()
