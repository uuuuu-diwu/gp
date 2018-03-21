#!/usr/bin/env python
# coding=utf-8

import os
import sys
import re
from pyltp import Segmentor
from pyltp import Postagger
ltp_data_dir = "/home/wd/Downloads/ltp_data_v3.4.0/"
segmentor = Segmentor()
postagger = Postagger() 
segmentor.load(ltp_data_dir + "cws.model")
postagger.load(ltp_data_dir + "pos.model")

in_file_name = "input.txt"
out_file_name = "output.txt"
begin_line = 1
end_line = 0

if len(sys.argv) > 1:
    in_file_name = sys.argv[1]

if len(sys.argv) > 2:
    out_file_name = sys.argv[2]

if len(sys.argv) > 3:
    begin_line = int(sys.argv[3])

if len(sys.argv) > 4:
    end_line = int(sys.argv[4])


def extraction_start(in_file_name, out_file_name, begin_line, end_line):
    """
    Args:
        in_file_name: 输入文件的名称
        #out_file_name: 输出文件的名称
        begin_line: 读文件的起始行
        end_line: 读文件的结束行
    """
    in_file = open(in_file_name, 'r')
    out_file = open(out_file_name, 'a')
    
    line_index = 1
    sentence_number = 0
    text_line = in_file.readline()
    while text_line:
        if line_index < begin_line:
            text_line = in_file.readline()
            line_index += 1
            continue
        if end_line != 0 and line_index > end_line:
            break
        sentence = text_line.strip()
        if sentence == "" or len(sentence) > 1000:
            text_line = in_file.readline()
            line_index += 1
            continue
	try:
            segmentOneSentence(sentence, out_file)
            out_file.flush()
        except:
            pass
        sentence_number += 1
        if sentence_number % 50 == 0:
            print "%d done" % (sentence_number)
        text_line = in_file.readline()
        line_index += 1
    in_file.close()
    out_file.close()


def segmentOneSentence(sentence, out_file):
	words = segmentor.segment(sentence)
	for word in words:
		out_file.write("%s " % word)
	out_file.write("\n")
def extractWordByCX(sentence,tag,out_file):
	words = segmentor.segment(sentence)
	postags = postagger.postag(words)
	for i in range(len(words)):
		if postags[i] == tag:
			out_file.write("%s " % words[i])
	out_file.write("\n")


def extractPargrah(in_file_name, out_file_name,paragraph,tag):
    in_file = open(in_file_name, 'r')
    out_file = open(out_file_name, 'a')
    isLine2Extract = False
    pattern = "【" + paragraph + "】"
    lines = in_file.readlines()
    count = 0
    for text_line in lines:
        if text_line.find(pattern) > 0:
            isLine2Extract = True
        if re.search("【.*】",text_line) and text_line.find(pattern) < 0:
            isLine2Extract = False
            print text_line
        if isLine2Extract and text_line.find(pattern) < 0:
            extractWordByCX(text_line,tag,out_file)
        else:
            count = count + 1
    print count
if __name__ == "__main__":
    #extraction_start(in_file_name, out_file_name, begin_line, end_line)
    #extraction_start(in_file_name, out_file_name, begin_line, end_line)
    extractPargrah(in_file_name,out_file_name + "lcbx.txt","临床表现","v")
    extractPargrah(in_file_name,out_file_name + "诊断和鉴别诊断.txt","诊断和鉴别诊断","v")
    extractPargrah(in_file_name,out_file_name + "病因.txt","病因","v")
    extractPargrah(in_file_name,out_file_name + "病因.txt","病因","v")


