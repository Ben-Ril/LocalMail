using LocalMailInstaller.Properties;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Resources;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace LocalMailInstaller
{
    public partial class MainForm : Form
    {
        private bool isConfig = false;
        private Panel mainPanel;
        private Panel installPanel;
        private Panel uninstallPanel;

        public MainForm()
        {
            mainPanel = new Panel();
            installPanel = new Panel();
            uninstallPanel = new Panel();
            SuspendLayout();
            
            Initializer();

            VerifyFiles();
            Place();

            InitializeComponent();
        }

        private void Initializer()
        {
            string actualLang = Settings.Default.lang;
            
            Thread.CurrentThread.CurrentUICulture = CultureInfo.GetCultureInfo(actualLang);

            Rectangle screenSize = Screen.FromControl(this).Bounds;
            Size = MinimumSize = MaximumSize = new Size(screenSize.Width / 2, screenSize.Height / 2);
            mainPanel.Size = installPanel.Size = uninstallPanel.Size = Size;
            MaximizeBox = false;
            MinimizeBox = false;
            FormBorderStyle = FormBorderStyle.FixedSingle;
            CenterToScreen();
        }

        private void VerifyFiles()
        {
            string installationPath = @Settings.Default.installationPath;

            if (installationPath != "none" && Directory.Exists(installationPath))
            {
                if(!File.Exists(installationPath + @"\config")){ isConfig = false; }
                else { isConfig = true; }
            }
            else{ isConfig = false; }
        }

        private void Place()
        {
            Button install = new Button();
            Button unInstall = new Button();

            int buttonWidth = (int)(Size.Width / 4);
            int buttonHeight = (int)(Size.Height / 4);

            install.Size = unInstall.Size = new Size(buttonWidth, buttonHeight);

            install.Location = new Point((int)(Size.Width / 2 - buttonWidth / 2), (int)(Size.Height / 2 - buttonHeight));
            unInstall.Location = new Point((int)(Size.Width / 2 - buttonWidth / 2), (int)(Size.Height / 2));

            install.Text = lang.Install;
            unInstall.Text = lang.Uninstall;

            if(!isConfig){unInstall.Enabled = false;}

            mainPanel.Controls.Add(install);
            mainPanel.Controls.Add(unInstall);

            Controls.Add(mainPanel);
        }
    }
}
