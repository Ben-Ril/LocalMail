# LocalMail

LocalMail is an internal mailbox allowing you to send mails to other users.<br>
It is originally a school project.

## Informations

- Actual Version: ``1.0``<br>
- Server Version: ``1.0``<br>
- Web Version: ``1.0``<br>
- API Version: ``1.0``<br>
- Installer Version: ``ACTUALY NOT AVAILABLE`` (when it will be available, many things will change on files management)
- Available languages: ``english``, ``francais``

---

## How to Install LocalMail

### Requirement

You will need to install:
- PHP 8.0 (with "sockets" extension enabled)
- Java 8
- Python 

You will need to have:
- A MySQL DataBase (e.g.: ``MariaDB``)

### Install

I - Download ``LocalMail.jar``, ``launcher.pyw``, ``web.zip`` and ``API.zip`` files.<br>
II - On a folder dedicated to LocalMail, copy ``launcher.pyw``.<br>
III - Create a folder ``server`` on the folder dedicated to LocalMail<br>
IV - Copy on ``server``, ``server.jar`` file<br>
V - On the dedicated folder to LocalMail, create a folder ``LocalMail``<br>
VI - Copy on ``LocalMail`` the content of ``web.zip`` and ``API.zip``<br>
VII - Create on the dedicated folder a file named ``config``<br>

### Configuration

The config file must contains multiples line:
```
adminUsername=USERNAME
adminPassword=PASSWORD
url=SQL_DB_URL
dbusername=SQL_DB_USERNAME
dbpassword=SQL_DB_PASSWORD
lang=english (or francais)
```
The lines order don't matter

### Start LocalMail

To start LocalMail you have to start ``launcher.py``
If you are on Windows and you start the launcher by double clicking the file, no console will open (and that's normal)<br>
Please, launch the launcher with admin right (on Windows)/sudo mode (on Linux)

### Config Users

I - Go to the admin panel (http://your.link/admin.php)<br>
II - Connect yourself to the panel (by using the username and the password defined on the ``config`` file)<br>
III - Once connected, create a user<br>

**NB**: The mail created by this user is on the format `FIRSTNAME.NAME@GROUP`
<br>
<br>
Congratulation ! LocalMail is now configured !

---

You can now acces to your mailbox (http://your.link/mailbox.php) !


Please note that we have'nt try LocalMail on Linux for the moment...

Thank you to using LocalMail !