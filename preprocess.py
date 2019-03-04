import jieba
import codecs
jieba.load_userdict('./user_dict.txt')
f = codecs.open("描述1.txt", encoding="utf-8")
new_d = codecs.open("new_data.txt", encoding="utf-8", mode="w")
new_l = codecs.open("new_label.txt", encoding="utf-8", mode="w")
lists = f.readlines()
print(len(lists))
for l in lists:
    s = jieba.cut(l)
    s = " ".join(s).split('\t')
    new_d.write(s[0] + '\n')
    new_l.write(s[1])
