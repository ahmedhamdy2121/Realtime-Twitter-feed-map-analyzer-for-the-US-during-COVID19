#!/bin/sh
# Jupyter Visualization Server installation

echo "Starting setting up Jupyter"

echo "downloading Anaconda Python 2.7"
wget https://repo.anaconda.com/archive/Anaconda2-2019.10-Linux-x86_64.sh

echo "Installing conda with python 2.7"
bash Anaconda2-2019.10-Linux-x86_64.sh
echo "conda with python 2.7 installed"

echo "Installing jupyterlab and notebook"
conda install -c conda-forge jupyterlab 
conda install -c conda-forge notebook 
echo "jupyterlab and notebook installed"

echo "Setting environment variables"
export PYSPARK_DRIVER_PYTHON=jupyter 
export PYSPARK_DRIVER_PYTHON_OPTS='notebook'
echo "Environment variables set"

echo "Updating spark environment"
cd /etc/spark
echo "export PYSPARK_PYTHON=/home/cloudera/anaconda2/bin/python" >> conf/spark-env.sh
echo "export SPARK_YARN_USER_ENV='PYSPARK_PYTHON=/home/cloudera/anaconda2/bin/python'" >> conf/spark-env.sh
echo "export PYSPARK_PYTHON=/home/cloudera/anaconda2/bin/python" >> conf.dist/spark-env.sh
echo "export SPARK_YARN_USER_ENV='PYSPARK_PYTHON=/home/cloudera/anaconda2/bin/python'" >> conf.dist/spark-env.sh
echo "export PYSPARK_PYTHON=/home/cloudera/anaconda2/bin/python" >> conf.cloudera.spark_on_yarn/spark-env.sh
echo "export SPARK_YARN_USER_ENV='PYSPARK_PYTHON=/home/cloudera/anaconda2/bin/python'" >> conf.cloudera.spark_on_yarn/spark-env.sh
echo "spark environment updated"

echo "Installing Lighting for Jupyter"
pip install lightning-python
echo "Lighting for Jupyter installed"

echo "Jupyter Visualization Server has been installed successfully"
echo "You can open a notebook by typing 'pyspark' in the terminal"
echo "then a webpage will open, you can select 'new notebook' from it"
echo "be sure that the 'Lighting Visualization Server' is up and running in another terminal window"