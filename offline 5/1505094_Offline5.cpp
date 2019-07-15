#include<iostream>
#include<cstdio>
#include<string>
#include<cstring>
#include<windows.h>
#include<cstdlib>
#include <bits/stdc++.h>

using namespace std;



vector<string> vec;
string vv;
vector<int> red;
vector<string> aaa;
//vector<datablck> dbl;


string padding(string s, int row)
{
    int h = s.size() % row;
    int hh = row - h;
    if(h != 0)
    {
        for(int i = 0; i < hh; i++)
        {
            s.append("~");
        }
    }


    return s;
}

void asciiPerMChar(string st,int row)
{
    int dataBlocks = st.size()/row;
    //int i = 0;
    string gh = "";

    for (int i = 1; i <= st.size(); i++)
    {
        int val = int(st[i-1]);

        string bin = "";
        while (val > 0)
        {
            (val % 2)? bin.push_back('1') : bin.push_back('0');
            val /= 2;
        }
        if(bin.size() != 8)
        {
            int tt = 8 - bin.size();
            for(int i = 0; i < tt; i++)
            {
                bin.push_back('0');
            }
        }
        reverse(bin.begin(), bin.end());
        gh.append(bin);
        if(i % row == 0)
        {
            vec.push_back(gh);
            gh.clear();
        }

    }
    cout<<"data block ( ascii code of m characters per row )"<<endl;
    for(int j = 0; j < vec.size(); j++)
    {
        cout<<vec.at(j)<<endl;
    }
    cout<<endl;


}

int getParity(int pos,string str)
{
    int cnt = 0;
    int k = 1;

    while(pos*k <= str.length())
    {
        for(int i = pos*k; i < pos*k+pos; i++)
        {
            if(str[i] == '1')
            {
                cnt++;
            }
        }
        k = k + 2;
    }
    // }
    return (cnt%2) ;

}

int getPower(string str)
{
    int r = 0;
    while(vec[0].size() > pow (2,r))
    {
        r++;
    }
    int extra_bits = r + 1;
    return extra_bits;
}

void addCheckbits()
{
    string zero = "0";
    //SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 15);//const char *z = zero.c_str();
    int r = 0;
    while(vec[0].size() > pow (2,r))
    {
        r++;
    }
    int extra_bits = r + 1 ;
    for(int i = 0; i < vec.size(); i++)
    {
        for(int j = 0; j < extra_bits && pow(2,j) <= vec[0].size(); j++)
        {
            vec[i].insert(pow(2,j)-1,zero);
        }

    }
    for(int i = 0; i < vec.size(); i++)
    {
        vec[i].insert(0,"A");
    }

    for(int i = 0; i < vec.size(); i++)
    {
        for(int j = 0; j < extra_bits; j++)
        {
            /*if(j == 5 ){
                vec[i][pow(2,j)] = 1 - getParity(pow(2,j),vec[i]) + '0';
            }else*/
            vec[i][pow(2,j)] = getParity(pow(2,j),vec[i]) + '0';
        }

    }
    int k;
    //while(k < extra_bits)
    for(int i = 0; i < vec.size(); i++)
    {
        k = 1;
        for(int j = 1; j < vec[0].size(); j++)
        {
            //for(int k = 0; k < extra_bits; k++){
            if(j == k)
            {
                SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 10);
                cout<<vec[i].at(j);
                k = k*2;
            }
            else
            {
                SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 15);
                cout<<vec[i].at(j);
            }
            //}
            //cout<<vec[i].at(j);
        }
        k++;
        cout<<endl;
    }
    //k++;
    //cout<<vec[0].size()<<endl;
}


string printColOrder()
{
    string ss="";
    for(int i = 1; i <vec[0].size(); i++)
    {
        for(int j = 0; j < vec.size(); j++)
        {
            string temp="A";
            temp[0]=vec[j][i];
            ss+=temp;
        }
    }
    cout<<"Col Order Print "<<endl;
    cout<<ss<<endl;
    vv = ss;
    return ss;
}

string crcString(string quo,string poly)
{
    string encoded="";
    int m = quo.length();
    int n = poly.length();
    encoded = encoded + quo;
    for(int i = 1; i < n; i++)
    {
        encoded += "0";
    }
    for(int i = 0; i <= encoded.length()-n;)
    {
        for(int j = 0; j < n; j++)
            if(encoded[i + j] == poly[j])
            {
                encoded[i+j] = '0';
            }
            else encoded[i+j] = '1';
        for(; i < encoded.length() && encoded[i] != '1'; i++);
    }
    string dd = encoded.substr(encoded.length()-n+1);
    return dd;
}

float jimsrand(void)
{
    double mmm = RAND_MAX;
    float x;                 /* individual students may need to change mmm */
    x = rand() / mmm;        /* x should be uniform in [0,1] */
    return (x);
}

string receiveWithError(float pr,string poly)
{
    vv.append(crcString(vv,poly));
    for(int i = 0; i < vv.length(); i++)
    {
        if(jimsrand() <= pr)
        {

            vv[i] = '1' - vv[i] + '0';

            SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 12);
            cout<<vv[i];
            red.push_back(1);
        }
        else
        {
            SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 15);
            cout<<vv[i];
            red.push_back(0);
        }
    }
    cout<<endl;
    return vv;
}

bool error(string poly)
{
    string w = crcString(vv,poly);
    bool f;
    for(int i = 0; i < w.length(); i++)
    {
        if(w[i] == '1')
        {
            f = false;
            break;
        }
        else f = true;
    }
    if(f == true) return true;
    else return false;
}

void deserialization(string input,int m,string st)
{
    int blocks = input.size()/m;
    for(int i = 0; i < blocks; i++)
    {
        aaa.push_back("");
    }
    int j = 0;
    while( j < st.length())
    {
        for(int i = 0; i < aaa.size(); i++)
        {
            aaa[i].push_back(st[j]);
            j++;
        }
    }
    cout<<"After De serialization/ Data blocks after removing CRC checksum bits "<<endl;
    for(int i = 0; i < blocks; i++)
    {
        for(int j = 0; j < aaa[0].size(); j++)
        {
            int id = blocks*j+i;
            if(red[id] == 1)
            {
                SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 12);
            }
            else
            {
                SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 15);
            }
            cout<<aaa[i].at(j);
        }
        cout<<endl;
    }
    SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 15);

}

void errorCorrection(float pr)
{
    string jj = "A";
    int extra_bits = getPower(aaa[0]);
    int sz = aaa[0].size();
    int k = 1,counter,v;
    if(pr > 0.0)
    {
        for(int i = 0; i < aaa.size(); i++){
            k = 1;
            counter = 0;
            while(k < sz){
                v = (int)aaa[i][k-1] - '0';
                counter = counter + v*k;
                k = k*2;
            }
            if(counter != 0 && counter < sz){
                v = (int)aaa[i][k-1] - '0';
                if(v == 0){
                    aaa[i].replace(counter-1,1,"1");
                }
                else aaa[i].replace(counter-1,1,"0");
            }
        }
        /*for(int i = 0; i < aaa.size(); i++)
        {
            for(int j = 0; j < extra_bits; j++)
            {
                int counter = 0;
                jj = jj + aaa[i];
                if(aaa[i][pow(2,j)-1] != getParity(pow(2,j),jj) + '0')
                {
                    counter = counter + pow(2,j);
                }
                jj = "A";
                if(counter == 0)
                {
                    aaa[i][pow(2,j)-1] = '1' - aaa[i][pow(2,j)-1] + '0';
                }
            }
        }*/
    }

}


void removeCheckBits(float rr)
{
    errorCorrection(rr);
    int rt = getPower(aaa[0]);
    //cout<<aaa[0].size()<< " "<< rt<<endl;

    for(int i = 0; i < aaa.size(); i++)
    {
        for(int j = 0; j < rt && pow(2,j) <= aaa[0].size(); j++)
        {
            aaa[i].erase(aaa[i].begin()+pow(2,j)-1-j);
        }

    }
    cout<<"data block After removing checkbits" <<endl;
    for(int i = 0; i < aaa.size(); i++)
    {
        cout<<aaa[i]<<endl;
    }
}

string lastTask(){
    string last = aaa[0];
    for(int i = 1; i < aaa.size(); i++){
        last.append(aaa[i]);
    }
    //cout<<last<<endl;
    return last;
}



int main()
{
    string st;
    int m;
    string pol;
    float pr;
    //str.erase(start_position_to_erase, number_of_symbols);
    cout<<"enter data string : ";
    getline(cin,st);

    cout<<"enter number of data bytes in a row : ";
    cin>>m;

    cout<<"Enter Probability : ";
    cin>>pr;

    cout<<"Enter polynomial : ";
    cin>>pol;
    string gg = padding(st,m);
    cout<<"data string after padding : "<<gg<<endl;
    cout<<gg.size()<<endl;
    asciiPerMChar(gg,m);
    addCheckbits();
    cout<<"data bits after column wise serialization : "<<endl;
    string pp = printColOrder();
    int iii=pp.length();
    string checksum = crcString(pp,pol);
    pp.append(checksum);
    cout<<"data bits after appending CRC checksum(sent frame) : "<<endl;
    for(int i = 0; i < pp.length(); i++)
    {
        if(i > iii - 1)
        {
            SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 11);
            cout<<pp[i];
        }
        else cout<<pp[i];
    }
    cout<<endl;
    cout<<"Received Frame : "<<endl;
    SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 15);
    string abc = receiveWithError(pr,pol);
    bool u = error(pol);
    if(u == true)
    {
        cout<<"result of CRC Checksum matching : no errors"<<endl;
    }
    else
    {
        cout<<"result of CRC Checksum matching : errors detected"<<endl;
    }
    string vvback = vv;
    abc.erase(abc.size()-checksum.size(),checksum.size());
    cout<<abc<<endl;
    deserialization(gg,m,abc);
    //errorCorrection(pr);
    removeCheckBits(pr);
    string pq = lastTask();
    string data = pq;
    stringstream sstream(data);
    string output="";
    while(sstream.good())
    {
        bitset<8> bits;
        sstream >> bits;
        char c = char(bits.to_ulong());
        output += c;
    }
    cout<<"Output frame : "<<output<<endl;
    return 0;
}
