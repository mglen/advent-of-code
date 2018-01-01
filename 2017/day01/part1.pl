# Day 1 answers in perl, attempting to use as much regex as possible

print 'Day 1 input: ';
my $data = <STDIN>;
chomp($data);

my ($result1) = $data =~ /^(\d).*?\1$/;
$result1 += $_ foreach ($data =~ /(\d)(?=\1)/g);
print "Part 1 result: $result1\n";

my ($result2, $half) = (0, length($data)/2-1);
$result2 += $_ * 2 foreach ($data =~ /(\d)(?=\d{$half}\1)/g);
print "Part 2 result: $result2\n";

# Part 1 Golfed:
# $d=<>;($r)=$d=~/^(\d).*?\1$/;$r+=$_ for($d=~/(\d)(?=\1)/g);say$r;

# Part 2 Golfed:
# $d=<>;chop$d;$r=0;$h=length($d)/2-1;$r+=$_*2 for($d=~/(\d)(?=\d{$h}\1)/g);say$r;
