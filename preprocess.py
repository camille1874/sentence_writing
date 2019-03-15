# import jieba
import codecs
# jieba.load_userdict('./user_dict.txt')
# f = codecs.open("描述1.txt", encoding="utf-8")
# new_d = codecs.open("new_data.txt", encoding="utf-8", mode="w")
# new_l = codecs.open("new_label.txt", encoding="utf-8", mode="w")
# lists = f.readlines()
# print(len(lists))
# for l in lists:
#     s = jieba.cut(l)
#     s = " ".join(s).split('\t')
#     new_d.write(s[0] + '\n')
#     new_l.write(s[1])
f = codecs.open("单一属性_类型.txt", encoding="utf-8")
new_f0 = codecs.open("车型_属性提出.txt", encoding="utf-8", mode="w")
new_f1 = codecs.open("车型_属性未提出.txt", encoding="utf-8", mode="w")
lists = f.readlines()
clean_list = ["排行榜", "销量", "报告", "市场", "消费", "如果你想买"]
for l in lists:
    flag = True
    for c in clean_list:
    	if c in l:
    		flag = False
    		break
    if not flag:
    	continue
    tmp = l.strip().split("\t")
    sen = tmp[0]
    label = tmp[1]
    new_f0.write(sen.replace(label, "0") + "\n")
    new_f1.write(sen + "\n")