#!/usr/bin/env python
import os,sys
import csv
import json
import fnmatch
import argparse


Keyords_to_ignore_list=["adal","libtrace", "aeea","clib","dbgx","dra","ecc","fcp","ffs","ffsfs","fpart","iomux","ppcdbg","registry","spinor","tiny_kmsglogd","tiny_syslogd","watchdog","-x86.bb","-x11.bb","bbclass",".conf",".bbappend","devmem2.bb"]

Keyords_to_check_valid_recipie_name_list=["0.bb","1.bb","2.bb","3.bb","4.bb","5.bb","6.bb","7.bb","8.bb","9.bb"]

Keywords_recipie_name=["sqlite3",\
						"fuse", \
						"libtirpc",\
						"rpcbind", \
						"tools-debug", \
						"libatomic",\
						"procps", \
						"zeromq", \
						"kmod",\
						"grpc",\
						"rpm", \
						"makedumpfile", \
						"openldap",\
						"lua", \
						"acl", \
						"libidn",\
						"gmp", \
						"nettle", \
						"libcap",\
						"libgpg-error", \
						"libgcrypt", \
						"pam",\
						"openssh", \
						"net-snmp", \
						"util-linux",\
						"tcpdump", \
						"pciutils", \
						"net-tools",\
						"krb5", \
						"xz", \
						"libz2",\
						"elfutils", \
						"libcap-ng", \
						"libxml2", \
						"liburcu",\
						"audit", \
						"ifenslave",\
						"ethtool", \
						"kexec-tools", \
						"udev",\
						"trace-cmd", \
						"gcc-runtime", \
						"busybox",\
						"base-files", \
						"base-passwd", \
						"initscripts", \
						"modutils-initscripts",\
						"tinylogin", \
						]	


class openbmcRootfsDescription:
    def __init__(self,openBMCprojectPath=".", Rootfspath = "."):
		self.usr_lib_path = Rootfspath+"/usr/lib"
		self.usr_lib_list=[]
		self.usr_bin_path = Rootfspath+"/usr/bin"
		self.usr_bin_list=[]
		self.lib_path = Rootfspath+"/lib"
		self.lib_list=[]
		self.bin_path = Rootfspath+"/bin"
		self.bin_list=[]
		self.Recipie_Dict_List={}
		self.openBMCprojectPath=openBMCprojectPath
		self.recipiepattern=".bb"
		
    def parse_openbmc_roots(self):
		usr_lib_path_files = os.listdir(self.usr_lib_path)
		for usr_lib in usr_lib_path_files:		
			skipflag=False
			for Keyords_to_ignore in Keyords_to_ignore_list:
				if Keyords_to_ignore in usr_lib: 
					skipflag=True
			if skipflag:
				continue 	
					
			lib_name=usr_lib.split(".")[0]
			if lib_name not in self.usr_lib_list:
				self.usr_lib_list.append(lib_name)
			         		
    def generate_recipie_name_dict(self):
		for name in self.usr_lib_list:
			if name.startswith('lib'):
				recipiename = name[len('lib'):]
				self.Recipie_Dict_List[recipiename]=name
			with open('Rootfs_lib_Name.txt', 'w') as file:
				file.write(json.dumps(self.Recipie_Dict_List))

    def find_recipie_names_openBMC_project(self):
		with open('openBMCrecipies_list.csv', mode='w') as csv_file:			
			fieldnames = ['recipie_name','recipie_version']
			writer = csv.DictWriter(csv_file, fieldnames=fieldnames)
			writer.writeheader()
			
			for root, directories, filenames in os.walk(self.openBMCprojectPath):
				for filename_path in filenames:
					filename=filename_path.split("/")[-1]					
					skipflag=False
					for Keyords_to_ignore in Keyords_to_ignore_list:
						if Keyords_to_ignore in filename: 
							skipflag=True
					if skipflag:
						continue 

					skipflag=False
					for Keyords_to_check_valid_recipie_name in Keyords_to_check_valid_recipie_name_list:
						if Keyords_to_check_valid_recipie_name in filename: 
							skipflag=True
							break
					if skipflag is False:
						continue 
					recipie_name=filename.split("_")[0]
										
					if recipie_name not in self.Recipie_Dict_List:
						if recipie_name not in Keywords_recipie_name:
							continue
						
					recipie_version=filename.split("_")[1].split(".b")[0]
					writer.writerow({'recipie_name': recipie_name,'recipie_version': recipie_version})		
									
if __name__ == '__main__':
	openBMCprojectPath='.'						
	Rootfspath = '.'

	parser = argparse.ArgumentParser()
	parser.add_argument("openBMCprojectPath", help="Provide the path of openBMC project")
	parser.add_argument("Rootfspath", help="Provide the path of openBMC generated fsp2-ppc rootfs ")
	
	args =parser.parse_args()
	openBMCprojectPath = args.openBMCprojectPath
	Rootfspath = args.Rootfspath
	

	openBMCrootfs = openbmcRootfsDescription(openBMCprojectPath,Rootfspath)

	openBMCrootfs.parse_openbmc_roots()

	openBMCrootfs.generate_recipie_name_dict()

	openBMCrootfs.find_recipie_names_openBMC_project()

